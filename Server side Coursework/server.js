// UP855834
'use static'

const express = require('express')
const app = express()

app.use(express.static("public"))
let img = require('./imager')

//********* Stat recording globals ************//
let recentReqs = []
let textList = []
let sizesList = []
let referrerList = []
let requestTimes = []
//********************************************//

function createFormattedPath(size, square, text){
  let finPath = "/img/" + size.width + "/" + size.height

  if (text != undefined){
    textList.unshift(text) // Record text before replacing whitespace with chars for stats
    text = text.split('+').join('%20')
    text = text.split(' ').join('%20')
    if (square == undefined){
      finPath += "?text=" + text
      }
    else{
      finPath+="?square=" + square + "&text=" + text
    }

  }else{
    return square == undefined? finPath : finPath + "?square=" + square

  }return finPath
}

function updateVars(size,square,text){
  pathForStats = createFormattedPath(size, square, text)
  recentReqs.unshift(pathForStats)
  sizesList.unshift(size)
  requestTimes.unshift(Date.now())
}

app.get('/img/:width/:height/:square?/:text?', function(req, res){
  let size = {width: parseFloat(req.params.width), height: parseFloat(req.params.height)}
  let square = req.query.square
  let text = req.query.text

  // Dimensions testing
  // Long && evaluates expressions in order
  if (Number.isInteger(size.width) && Number.isInteger(size.height) && size.height > 0 && size.width > 0 && size.height <= 2000 && size.width <= 2000) {

    if (req.header('Referer') !== undefined){ // Track referrer headers where necessary
      referrerList.unshift(req.header('Referer'))
      }

    if (square === undefined){ //
      img.sendImage(res,parseInt(size.width), parseInt(size.height), null, text)
      updateVars(size, square, text)

  // Square Testing
    }else try{
      square = parseFloat(square)
      if (square > 0 && Number.isInteger(square)){
        img.sendImage(res,parseInt(size.width), parseInt(size.height), parseInt(square), text)
        updateVars(size, square, text)

      }else{ // square is negative or float
        res.sendStatus(400)
      }

    }catch(e){ //square is NaN
      res.sendStatus(400)
    }

  }else{ //Determines error type
    (size.height > 2000 || size.width > 2000) ? res.sendStatus(403) : res.sendStatus(400)
  }
  
})

app.get('/stats/paths/recent', function(req, res){
  uniqueReqs = [...new Set(recentReqs)] // Remove pathname duplications
  uniqueReqs.splice(10) // Show first 10
  res.send(uniqueReqs)
})

function formatRecentSizes(sObj){
  size = JSON.stringify(sObj)
  size = size.split('width').join('w')
  size = size.split('height').join('h')
  return size
}

app.get('/stats/sizes/recent', function(req, res){
  recentSizes = sizesList.map(size=>formatRecentSizes(size)) //Convert to required string
  recentSizes = [...new Set(recentSizes)] //Remove duplicate strings
  recentSizes = recentSizes.map(size=>JSON.parse(size)) //Convert string back to JSON
  recentSizes.splice(10)
  res.send(recentSizes)
})

app.get('/stats/texts/recent', function(req, res){
  textList = [...new Set(textList)]
  textList.splice(10)
  res.send(textList)
})

function formatTopSize(size, sizeDict){
  let amount = (sizeDict[size])
  size = JSON.parse(size)
  size.n = amount
  return size
}

function createCountDict(objList){
  let countDict = {}
  for (let row of objList){
    if (row in countDict){
      countDict[row] += 1
    }else{
      countDict[row] = 1
    }
  }return countDict
}

app.get('/stats/sizes/top', function(req,res){
  topSizes = sizesList.map(size => formatRecentSizes(size))
  let sizesDict = createCountDict(topSizes) //Creates dict of {w:x, h:y} : Number of times this size is requested
  topSizes = [...new Set(topSizes)]
  topSizes = topSizes.map(size=>formatTopSize(size, sizesDict)) // => [{w:x, h:y,n:num},...]
  topSizes.sort((a,b) => Number(b.n) - Number(a.n)) //Shorthand for descending order using size.n
  topSizes.splice(10)
  res.send(topSizes)
})

function formatTopRefers(ref,req,referrerDict){
  let amount = (referrerDict[ref])
  refObj = {ref: ref, n: amount}
  return refObj
}

app.get('/stats/referrers/top', function(req,res){
  let referrerDict = createCountDict(referrerList)
  topReferrers = [...new Set(referrerList)]
  topReferrers = topReferrers.map(ref => formatTopRefers(ref, req, referrerDict))
  topReferrers.splice(10)
  res.send(topReferrers)
})

app.delete('/stats', function(req,res){
  recentReqs = []
  textList = []
  sizesList = []
  referrerList = []
  requestTimes = []
  res.sendStatus (200)
})

app.get('/stats/hits', function(req,res){
  let startTime = Date.now()
  let counter = [{title: '5s', count: 0},
                 {title: '10s', count: 0},
                 {title: '15s', count: 0}]

  for (let i of requestTimes){
    let diff = startTime - i //Time when /stats/hits request loaded - each time recorded on successful /img request

    if (diff < 5000){ // 5s, count hits are cumulative
      counter[0].count += 1
    }
    if (diff < 10000){
      counter[1].count += 1
    }
    if (diff < 15000){
      counter[2].count += 1
    }
  }
  res.send(counter)
})

app.listen(8080)
