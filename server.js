const express = require('express');
const path = require('path');
const cors = require('cors');
const app = express();

app.use(cors());

// Using middleware
app.use(express.static(path.join(__dirname, 'dist/WeatherSearch')));

// Example: Getting out POSTS routes
const posts = require('./server/routes/posts');
app.use('/posts', posts);

// GOOGLE GEOCODE API
const geocode = require('./server/routes/geocode');
app.use('/geo', geocode);

// Google Auto Complete
const placeAuto = require('./server/routes/placeAutoComplete');
app.use('/placeauto', placeAuto);

// DarkSky forecast
const forecast = require('./server/routes/forecast');
app.use("/forecast", forecast);

// Google SEAL
const seal = require('./server/routes/seal')
app.use("/seal", seal);

// Google City Views
const cityview = require('./server/routes/cityviews')
app.use("/cityviews", cityview);

// Catch all other requests and return it to index
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, 'dist/WeatherSearch/index.html'));
});

const port = process.env.PORT || 4600;
app.listen(port, (req, res) => {
  console.log(`Running on port ${port}`);
});
