const webpack = require("webpack");
const ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = require('./webpack.config.js');    // inherit from the main config file

// extract css text
module.exports.plugins.push(
  new ExtractTextPlugin('../css/main.css')
);
