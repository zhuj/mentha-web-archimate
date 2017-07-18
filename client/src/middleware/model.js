import * as actions from '../actions'

const CALL_API = 'CallAPI'

export const modelConnect = (endpointUrl) => ({
    type: "WS_MODEL_CONNECT",
    [CALL_API]: {
        type: "CONNECT",
        endpointUrl: "ws://127.0.0.1:8088/" + endpointUrl // TODO: obtain & use real address
    }
})

export const modelDisconnect = () => ({
    type: "WS_MODEL_DISCONNECT",
    [CALL_API]: {
        type: "DISCONNECT"
    }
})

export const modelSendMessage = (payload) => ({
    type: "WS_MODEL_SEND_MESSAGE",
    [CALL_API]: {
        type: "MESSAGE",
        payload: payload
    }
})

const messageHandler = (name, handler) => ({ name, handler })
const messageHandlers = {
    "noop": messageHandler("noop", actions.modelNoopReceived),
    "object": messageHandler("object", actions.modelObjectReceived),
    "commit": messageHandler("commit", actions.modelCommitReceived),
    "error": messageHandler("error", actions.modelErrorReceived)
}

const messageReceived = (store, messageBody) => {
    try {
        const payload = JSON.parse(messageBody);
        const tp = payload['_tp']
        const { name, handler } = messageHandlers[tp]
        store.dispatch(handler(payload[name]));
    } catch(exc) {
        console.error(exc);
        store.dispatch(actions.modelErrorReceived(exc.toString()));
    }
}

export const createModelMiddleware = function(){
  var socket = null;

  const onOpen = (ws,store,token) => evt => {
    //TODO: Send a handshake, or authenticate with remote end

    //Tell the store we're connected
    store.dispatch(actions.modelConnected());
  }

  const onClose = (ws,store) => evt => {
    //Tell the store we've disconnected
    store.dispatch(actions.modelDisconnected());
  }

  const onMessage = (ws,store) => evt => {
    //Parse the JSON message received on the websocket
    messageReceived(store, evt.data);
  }

  return store => next => action => {
    const call = action[CALL_API]
    if (typeof(call) === 'undefined') {
      return next(action)
    }

    switch(call.type) {

      //The user wants us to connect
      case 'CONNECT':

        //Start a new connection to the server
        if (socket != null) {
          socket.close();
        }

        //Send an action that shows a "connecting..." status for now
        store.dispatch(actions.modelConnecting());

        //Attempt to connect (we could send a 'failed' action on error)
        socket = new WebSocket(call.endpointUrl);
        socket.onmessage = onMessage(socket,store);
        socket.onclose = onClose(socket,store);
        socket.onopen = onOpen(socket,store,action.token);

        break;

      //The user wants us to disconnect
      case 'DISCONNECT':
        if(socket != null) {
          socket.close();
        }
        socket = null;

        //Set our state to disconnected
        store.dispatch(actions.modelDisconnected());
        break;

      //Send the 'SEND_MESSAGE' action down the websocket to the server
      case 'MESSAGE':
        socket.send(JSON.stringify(call.payload));
        break;

      //This action is irrelevant to us, pass it on to the next middleware
      default:
        return next(action);
    }
  }

};
