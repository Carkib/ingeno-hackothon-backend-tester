var config = {
    entry: './main.js',

    output: {
        path: './',
        filename: 'public/index.js',
    },

    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loader: 'babel-loader',

                query: {
                    presets: ['es2015', 'react']
                }
            }
        ]
    },
    watch: true
};

module.exports = config;
