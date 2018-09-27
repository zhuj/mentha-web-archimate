const webpack = require('webpack');
const merge = require('webpack-merge');
const config = require('./webpack.config.base');

const env = {
    NODE_ENV: JSON.stringify('production')
};

const GLOBALS = {
    'process.env': env
};

module.exports = merge(config, {
    optimization: {
        minimize: true
    },
    devtool: 'source-map',
    entry: {
        app: [
            'babel-polyfill',
            'index'
        ]
    },
    plugins: [
        new webpack.NoEmitOnErrorsPlugin(),
        new webpack.DefinePlugin(GLOBALS),
        new webpack.LoaderOptionsPlugin({
            minimize: true,
            debug: false
        }),
        // new webpack.ExtractTextPlugin({
        //     filename: 'css/[name].css',
        //     allChunks: true
        // })
    ],
    // module: {
    //     rules: [
    //         {
    //             test: /\.css$|\.scss$/,
    //             use: ExtractTextPlugin.extract({
    //                 fallback: 'style-loader',
    //                 use: ['css-loader', 'sass-loader']
    //             })
    //         }
    //     ]
    // }
});

