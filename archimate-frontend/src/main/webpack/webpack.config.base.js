// Common Webpack configuration: used by others webpack configurations
const path = require('path');
const webpack = require('webpack');

const BINARY_FILE_MAX_SIZE = 8192;

const BASE_DIR = '../javascript';
const OUTPUR_DIR = '../resources/public';

module.exports = {
    optimization: {
        minimize: false,
        splitChunks: {
            filename: 'js/vendor.js',
            minChunks: Infinity
        }
    },
    entry: {
        vendor: [
            'react',
            'react-dom',
            'react-redux',
            'redux'
        ],
    },
    output: {
        filename: 'js/[name].js',
        path: path.resolve(__dirname, OUTPUR_DIR),
        publicPath: '/'
    },
    resolve: {
        alias: {
            src: path.join(__dirname, BASE_DIR)
        },
        modules: [
            path.join(__dirname, BASE_DIR),
            'node_modules'
        ],

        extensions: ['.js', '.jsx', '.json', '.scss']
    },
    module: {
        rules: [
            // JavaScript / ES6
            {
                test: /\.(jsx|js)$/,
                include: path.join(__dirname, BASE_DIR),
                loader: 'babel-loader'
            },
            // JSON
            {
                test: /\.(json)$/,
                loader: 'json-loader'
            },
            // Images
            // Inline base64 URLs for <=8k images, direct URLs for the rest
            {
                test: /\.(png|jpg|jpeg|gif|svg)$/,
                loader: 'url-loader',
                query: {
                    limit: BINARY_FILE_MAX_SIZE,
                    name: 'images/[name].[ext]?[hash]'
                }
            },
            // Fonts
            {
                test: /\.(woff|woff2|ttf|eot)(\?v=\d+\.\d+\.\d+)?$/,
                loaders: [
                    {
                        loader: 'url-loader',
                        query: {
                            limit: BINARY_FILE_MAX_SIZE,
                            name: 'fonts/[name].[ext]?[hash]'
                        }
                    }
                ]
            },
            {
                test: /\.css$|\.scss$/,
                loaders: [
                    'style-loader', 'css-loader', {
                        loader: 'sass-loader',
                        query: { outputStyle: 'expanded' }
                    }
                ]
            }
        ]
    }
};
