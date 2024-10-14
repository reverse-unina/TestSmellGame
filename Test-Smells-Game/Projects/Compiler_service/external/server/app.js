const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const server = require('http').Server(app);
const port = 8083;
const helmet = require('helmet');
const refactoring_service = require('./refactoring-service')
const utils = require('./utils')
const cors = require('cors');
var queue = require('express-queue');
const rateLimit = require('express-rate-limit');


/*
const apiLimiter = rateLimit({
    windowMs: 15 * 60 * 1000, // 15 minutes
    max: 100, // Limit each IP to 100 requests per `window` (here, per 15 minutes)
})

app.use('/compiler/refactoring', apiLimiter)
*/

app.use(helmet());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use(queue({ activeLimit: 1, queuedLimit: 10 }));
app.use(cors());

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});



utils.configEnvironment("PRODUCTION")


app.options('*', function(req, res) {
    res.send(200);
});

server.listen(port, (err) => {
    if (err) {
        throw err;
    }
    /* eslint-disable no-console */
    console.log("Developed by DARIO TINTORE")
    console.log('Endpoint started');
});

app.post('/compiler/refactoring',async (req, res) => {
    try{
        const data = req.body;
        let result = await refactoring_service.doCompile(data)
        result.testResult = utils.cleanSuccessResponse(result.testResult)
        if(result.success)
            result.smellResult = utils.removeIgnoredSmells(result.smellResult, data.exerciseConfiguration);
        console.log(result);
        res.status(200);
        res.json({ testResult: result.testResult,
                        similarityResponse: result.similarityResponse,
                        smellResult: result.smellResult,
                        success: result.success,
                        originalCoverage: result.originalCoverage,
                        refactoredCoverage: result.refactoredCoverage
        })
        res.end();
    }catch(error){
        console.log(error);
    }
});

module.exports = server;