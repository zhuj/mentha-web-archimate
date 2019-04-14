const webpack = require('webpack');
const merge = require('webpack-merge');
const config = require('./webpack.config.base');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

const env = {
    NODE_ENV: JSON.stringify('production')
};

const GLOBALS = {
    'process.env': env
};

module.exports = merge(config, {
    mode: 'production',
    devtool: 'source-map',
    optimization: {
        minimizer: [new UglifyJsPlugin()],
        splitChunks: {
            cacheGroups: {
                vendor: {
                    chunks: 'initial',
                    name: 'vendor',
                    test: 'vendor',
                    enforce: true
                },
            }
        },
    },
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
        new ExtractTextPlugin({
            filename: 'css/[name].css',
            allChunks: true
        })
    ],
    module: {
        rules: [
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

