//importScripts('/cache-polyfill.js');

//Attempting to cache bootstrap files for quicker loading

//self.addEventListener('install', function(e) {
// e.waitUntil(
//   caches.open('airhorner').then(function(cache) {
//     return cache.addAll([
//       '/',
//       '/index.html',
//       '/index.html?homescreen=1',
//       '/?homescreen=1',
//       '/styles/main.css',
//       '/scripts/main.min.js',
//       '/sounds/airhorn.mp3'
//     ]);
//   })
// );
//});

alert("TEST")

self.addEventListener('fetch',() => console.log("Fetching data.."));