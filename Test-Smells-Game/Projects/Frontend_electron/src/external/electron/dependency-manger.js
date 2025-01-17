const fs = require('fs');


function editPom(exerciseConfiguration){
  let data
  data = fs.readFileSync(process.env.ROOT_PATH + "src/external/compiler/tesi/pom_template.txt", 'utf8')
  let dependencies = "";
  exerciseConfiguration.dependencies.forEach(dep => dependencies += dep);
  data = data.replace('{dependency}', dependencies)
  fs.writeFileSync(process.env.ROOT_PATH + "src/external/compiler/tesi/pom.xml", data)
}

module.exports = {
  editPom,
}
