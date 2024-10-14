const cheerio = require('cheerio')
const fs = require('fs')
const dm = require('./dependency-manger')



function getPercentages(){
  let index1 = cheerio.load(fs.readFileSync(process.env.ROOT_PATH + 'external/compiler/tesi/original-module/target/site/jacoco/index.html'));
  let index2 = cheerio.load(fs.readFileSync(process.env.ROOT_PATH + 'external/compiler/tesi/refactored-module/target/site/jacoco/index.html'))
  let index3 = cheerio.load(fs.readFileSync(process.env.ROOT_PATH + 'external/compiler/tesi/aggregate-reports/target/site/jacoco-aggregate/index.html'));

  let first_coverage = index1('tbody tr .ctr2').html().replace('%','')
  let second_coverage = index2('tbody tr .ctr2').html().replace('%','')
  let total = index3('tfoot tr .ctr2').html().replace('%','')

  return [first_coverage, second_coverage, total]
}
async function checkSimilarity(exerciseConfiguration){
  let percentages = getPercentages()
  let first_coverage = Number(percentages[0])
  let second_coverage = Number(percentages[1])
  let total = Number(percentages[2])
  const acceptance = exerciseConfiguration.refactoring_limit;

  if(first_coverage === second_coverage && first_coverage === total){
    return [true, first_coverage, second_coverage]
  }
  else{
    let first_difference = total - first_coverage
    let second_difference = total - second_coverage
    if(second_difference < first_difference)
      return [true, first_coverage,second_coverage]
    let percentage_difference = Math.abs(first_difference - second_difference)
    return [percentage_difference <= acceptance,first_coverage, second_coverage]
  }
}

module.exports = {
  checkSimilarity
}

