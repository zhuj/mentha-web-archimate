const webpack = require('webpack');
const merge = require('webpack-merge');
const config = require('./webpack.config.base');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

const env = {
    NODE_ENV: JSON.stringify('production')
};

const GLOBALS = {
    'process.env': env
};

module.exports = merge(config, {
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
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false,
                screw_ie8: true
            },
            output: {
                comments: false
            }
        }),
        new webpack.LoaderOptionsPlugin({
            minimize: true,
            debug: false
        }),
        new ExtractTextPlugin({
            filename: 'css/[name].css',
            allChunks: true
        })
    ],
    module: {
        loaders: [
            {
                test: /\.css$|\.scss$/,
                use: ExtractTextPlugin.extract({
                    fallback: 'style-loader',
                    use: ['css-loader', 'sass-loader']
                })
            }
        ]
    }
});

