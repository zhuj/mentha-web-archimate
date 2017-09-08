const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

const app_root = 'src'; // the app root folder: src, src_users, etc

module.exports = {
  entry: [
    'babel-polyfill',
    __dirname + '/' + app_root + '/index.js',
  ],
  devtool: 'inline-source-map',
  output: {
    path: __dirname + '/public/js',
    publicPath: 'js/',
    filename: 'bundle.js',
  },
  devServer: {
    contentBase: __dirname + '/public',
    hot: true,
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    // new ExtractTextPlugin('../css/main.css')
  ],
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /(node_modules|bower_components)/,
        use: ['react-hot-loader', 'babel-loader'],
        // use: [
        //   { loader: 'react-hot-loader' },
        //   { loader: 'babel-loader' }
        // ]
      },
      {
        test: /\.scss$/,
        use: ['style-loader', 'css-loader', 'sass-loader'],
        // use: ExtractTextPlugin.extract({
        //   fallback: 'style-loader',
        //   use: ['css-loader', 'sass-loader']
        // })
      },
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader'],
        // use: ExtractTextPlugin.extract({
        //   fallback: 'style-loader',
        //   use: ['css-loader']
        // })
      }
    ]
  }
};

