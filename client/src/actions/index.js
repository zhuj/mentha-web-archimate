
import { modelConnect, modelDisconnect, modelSendMessage } from '../middleware/model/index'

export const connectModel = (id) => (dispatch, getState) => {
    return dispatch( modelConnect(`model/${id}`) )
};

export const disconnectModel = () => (dispatch, getState) => {
    return dispatch( modelDisconnect() )
};

export const sendModelMessage = (payload) => (dispatch, getState) => {
    return dispatch( modelSendMessage(payload) )
};

export const MODEL_CONNECTED = /*Symbol*/("MODEL_CONNECTED")
export const modelConnected = () => ({
    type: MODEL_CONNECTED
});

export const MODEL_CONNECTING = /*Symbol*/("MODEL_CONNECTING")
export const modelConnecting = () => ({
    type: MODEL_CONNECTING
});

export const MODEL_DISCONNECTED = /*Symbol*/("MODEL_DISCONNECTED")
export const modelDisconnected = () => ({
    type: MODEL_DISCONNECTED
});

export const MODEL_NOOP_RECEIVED = /*Symbol*/("MODEL_NOOP_RECEIVED")
export const modelNoopReceived = (payload) => ({
    type: MODEL_NOOP_RECEIVED,
    payload: payload
});

export const MODEL_OBJECT_RECEIVED = /*Symbol*/("MODEL_OBJECT_RECEIVED")
export const modelObjectReceived = (payload) => ({
    type: MODEL_OBJECT_RECEIVED,
    payload: payload
});

export const MODEL_COMMIT_RECEIVED = /*Symbol*/("MODEL_COMMIT_RECEIVED")
export const modelCommitReceived = (payload) => ({
    type: MODEL_COMMIT_RECEIVED,
    payload: payload
});

export const MODEL_ERROR_RECEIVED = /*Symbol*/("MODEL_ERROR_RECEIVED")
export const modelErrorReceived = (payload) => ({
    type: MODEL_ERROR_RECEIVED,
    payload: payload
});

export const RJD_UPDATE_MODEL = /*Symbol*/("RJD_UPDATE_MODEL")
export const rjdUpdateModel = (id, model, props) => ({
    type: RJD_UPDATE_MODEL,
    id: id,
    model: model,
    props: props
});


