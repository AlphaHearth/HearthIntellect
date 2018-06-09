const program = require('commander');
const MongoClient = require('mongodb').MongoClient;

program
    .version('0.1.0')
    .option('-e, --environment <e>', 'An environment of the working project', /^(dev|prod)$/i, 'dev')
    .option('-o, --origin <o>', 'The original host added to Access-Control-Allow-Origin header', ['http://localhost', 'http://localhost:4200'])
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
    console.log('数据库已创建!!!!');
    Setting.prototype.db = db.db('hearthstone');
    Setting.prototype.db.collection('cards').ensureIndex({name: 'text', text: 'text'});
});

module.exports.getSetting = getSetting;

