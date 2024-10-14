const {app, BrowserWindow, ipcMain, Menu, dialog} = require('electron')
const url = require("url");
const path = require("path");
const utils = require('./utils')
const refactoring_service = require('./refactoring-service')
const repo = require('./repository-service')

utils.configEnvironment("PRODUCTION")

let mainWindow

function createWindow () {
  mainWindow = new BrowserWindow({
    width: 1920,
    height: 1080,
    webPreferences: {
      nodeIntegration: true,
      enableRemoteModule: true,
      contextIsolation: false,
      devTools: true
    }
  })



  mainWindow.loadURL(
    url.format({
      pathname: path.join(process.env.ROOT_PATH, `/dist/dariotintore_frontend/index.html`),
      protocol: "file:",
      slashes: true
    })
  );
  // Open the DevTools.
//mainWindow.webContents.openDevTools()

  mainWindow.on('closed', function () {
    mainWindow = null
  })
}


function showJavaMessage(){
  const options = {
    type: 'error',
    buttons: ['Ok'],
    defaultId: 1,
    title: 'Java',
    message: 'Do you have installed java?',
    detail: 'The software may not work if you haven\'t installed java',
  };
  dialog.showMessageBox(null,options);
}

function showMavenMessage(){
  const options = {
    type: 'error',
    buttons: ['Ok'],
    defaultId: 1,
    title: 'Maven',
    message: 'Do you have installed Maven?',
    detail: 'The software may not work if you haven\'t installed maven',
  };
  dialog.showMessageBox(null,options);
}



app.on('ready', async function () {
  createWindow()
})

app.on('window-all-closed', function () {
  if (process.platform !== 'darwin') app.quit()
})

app.on('activate', function () {
  if (mainWindow === null) createWindow()
})

ipcMain.on('compile',async (event, data) => {
  let result = await refactoring_service.doCompile(data)
  result.testResult = utils.cleanSuccessResponse(result.testResult)
  console.log(result)
  if(result.success)
    result.smellResult = utils.removeIgnoredSmells(result.smellResult, data[1]);
  console.log(result);
  mainWindow.webContents.send('refactoring-exercise-response', result)
})

ipcMain.on('getFilesFromRemote', async (event, data) => {
  await repo.cloneRepository(data)
  let result = repo.getExerciseFilesFromLocal()
  mainWindow.webContents.send('getExerciseFilesFromLocal', result)
})

ipcMain.on('getProductionClassFromLocal', async(event,data)=> {
  let result = await repo.getProductionFilesFromLocal(data);
  mainWindow.webContents.send('receiveProductionClassFromLocal', result)
})

ipcMain.on('getTestingClassFromLocal', async(event,data)=> {
  let result = await repo.getTestingFilesFromLocal(data);
  mainWindow.webContents.send('receiveTestingClassFromLocal', result)
})

ipcMain.on('getCheckGameConfigFromLocal', async(event,data)=> {
  let result = await repo.getCheckGameConfiguration(data);
  mainWindow.webContents.send('receiveCheckGameConfigFromLocal', result)
})

ipcMain.on('getRefactoringGameConfigFromLocal', async(event,data)=> {
  let result = await repo.getRefactoringGameConfiguration(data);
  mainWindow.webContents.send('receiveRefactoringGameConfigFromLocal', result)
})

ipcMain.on('checkDependencies', async(event,data)=> {
  await utils.checkMaven().then(result => {
    if (result[0] !== true) {
      showMavenMessage()
      result.maven = true;
    }
    if (result[1] !== true) {
      showJavaMessage();
      result.java = true;
    }
    mainWindow.webContents.send('receiveDependenciesCheck', result)
  })
})

ipcMain.on('getConfigFilesFromLocal', async(event, data)=> {
  let result = await repo.getConfigFilesFromLocal(data);
  mainWindow.webContents.send('receiveConfigFilesFromLocal', result)
})

