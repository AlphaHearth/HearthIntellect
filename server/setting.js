const program = require('commander');
const MongoClient = require('mongodb').MongoClient;

const logger = require('./logging').logger;

program
    .version('0.1.0')
    .option('-e, --environment <e>', 'An environment of the working project', /^(dev|production)$/i, 'production')
    .option('-m, --mongo <m>', 'The url of mongodb','mongodb://hearthintellect-mongo:27017/')
    .parse(process.argv);

function Setting(program) {
    this.environment = program.environment;
    this.origin = program.origin;
}

function getSetting() {
   return new Setting(program);
}

MongoClient.connect(program.mongo, function (err, db) {
    if (err)
        throw err;
    logger.info('数据库已创建!');
    Setting.prototype.db = db.db('hearthstone');
    Setting.prototype.db.collection('cards').ensureIndex({name: 'text', text: 'text'});
});

module.exports.getSetting = getSetting;

