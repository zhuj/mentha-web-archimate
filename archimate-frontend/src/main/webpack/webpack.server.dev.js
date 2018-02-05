// Creates a hot reloading development environment
/* eslint no-console: "off" */
require('colors');
const compression = require('compression');
const config = require('./webpack.config.dev');
const detectPort = require('detect-port');
const path = require('path');
const express = require('express');
const webpack = require('webpack');
const webpackProxyMiddleware = require('http-proxy-middleware');
const webpackHotMiddleware = require('webpack-hot-middleware');
const webpackDevMiddleware = require('webpack-dev-middleware');


const app = express();
const compiler = webpack(config);

const DEFAULT_PORT = 8080;

const hookStream = function hookStream(stream, data, cb) {
  const oldWrite = stream.write;

  // New stream write with our shiny function
  stream.write = (...args) => {
    // Old behaviour
    oldWrite.apply(stream, args);
    // Hook
    if (args[0].match(data)) {
      cb();
    }
  };
};

const runServer = function runServer(localip, freeport) {
  const address = ('http://' + localip + ':' + freeport).underline.green;

  app.use(compression());

  app.use(webpackDevMiddleware(compiler, {
    https: true,
    publicPath: config.output.publicPath,
    hot: true,
    quiet: false,
    noInfo: false,
    lazy: false,
    stats: 'normal',
  }));

  app.use(webpackHotMiddleware(compiler, {
    path: '/__webpack_hmr'
  }));

  app.use(express.static(path.resolve(__dirname, '../resources/public')));

  const wsProxy = webpackProxyMiddleware('ws://127.0.0.1:8088/model', { secure:false, ws:true });
  app.use('/model/*', wsProxy);

  app.get('*', (req, res) => {
    res.sendFile(path.resolve(__dirname, '../resources/public/index.html'));
  });

  const server = app.listen(freeport, localip, (err) => {
    if (err) {
      console.log(err);
      return;
    }

    console.log('Listening at', address);

    hookStream(process.stdout, 'webpack built', () => {
      console.log('Running at', address);
    });
  });

  server.on('upgrade', wsProxy.upgrade);

};

const findFreePort = function findFreePort(port, next) {
  detectPort(port, (err, freeport) => {
    if (err) {
      console.log(err);
    } else {
      next(freeport);
    }
  });
};

findFreePort(DEFAULT_PORT, (port) => runServer('localhost', port));
