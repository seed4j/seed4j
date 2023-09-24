<template src="./LandscapeMiniMap.html"></template>

<script lang="ts" src="./LandscapeMiniMap.component.ts"></script>

<style>
#minimap-container {
  position: fixed;
  bottom: 20px;
  left: 20px;
  min-width: 20px;
  z-index: 100;
  background-color: #0000009a;
  overflow: hidden;
}

#minimap-size {
  position: relative;
  z-index: 5;
  border: 3px solid white;
}

#minimap-viewer {
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  transform-origin: 0 0;
  z-index: 100;
  border: 1px solid #fff;
}

#minimap-content {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
  transform-origin: 0 0;
  border: 1px solid white;
}
</style>
