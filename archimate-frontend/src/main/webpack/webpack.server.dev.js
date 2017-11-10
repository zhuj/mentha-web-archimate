// Creates a hot reloading development environment
/* eslint no-console: "off" */
require('colors');
const compression = require('compression');
const config = require('./webpack.config.dev');
const detectPort = require('detect-port');
const http = require('http');
const path = require('path');
const express = require('express');
const webpack = require('webpack');

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

    //app.use(proxyMiddleware);

    app.use(require('webpack-dev-middleware')(compiler, {
        https: true,
        publicPath: config.output.publicPath,
        hot: true,
        quiet: false,
        noInfo: false,
        lazy: false,
        stats: 'normal',
    }));

    app.use(require('webpack-hot-middleware')(compiler, {
        path: '/__webpack_hmr'
    }));

    app.use(express.static(path.resolve(__dirname, '../resources/public')));

    app.get('*', (req, res) => {
        res.sendFile(path.resolve(__dirname, '../resources/public/index.html'));
    });

    app.listen(freeport, localip, (err) => {
        if (err) {
            console.log(err);

            return;
        }

        console.log('Listening at', address);

        hookStream(process.stdout, 'webpack built', () => {
            console.log('Running at', address);
        });
    });
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
