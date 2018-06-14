const winston = require('winston');
const config = winston.config;

var logger = new (winston.Logger)({
    transports: [
        new (winston.transports.Console)({
            handleExceptions: true,
            timestamp: function () {
                return new Date();
            },
            formatter: function (options) {
                // - Return string will be passed to logger.
                // - Optionally, use options.colorize(options.level, <string>) to
                //   colorize output based on the log level.
                return options.timestamp() + ' ' +
                    config.colorize(options.level, options.level.toUpperCase()) + ' ' +
                    (options.message ? options.message : '') +
                    (options.meta && Object.keys(options.meta).length ? '\n\t' + JSON.stringify(options.meta) : '');
            }
        })
    ]
});

module.exports.logger = logger;