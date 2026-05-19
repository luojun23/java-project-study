<template>
  <div class="parent">
    <div class="child1"></div>
    <div class="child2"></div>
    <div class="child"></div>
    <div class="child"></div>
  </div>
</template>

<script setup>

</script>

<style>
body{
  padding: 0;
  margin: 0;
  width: 100%;
  height: 200vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  box-sizing: border-box;
  border: 10px solid red;
}

.parent{
  display: flex;
  gap: 10px;
  justify-content: center;
}

.child1{
  background-color: blue;
  width: 100px;
  height: 100px;
}

.child2{
  background-color: gold;
  width: 100px;
  height: 100px;
  position: absolute;
  top: 20px;
  right: 20px;
  position: fixed;
}

.child{
  width: 100px;
  height: 100px;
  background-color: red;
}
</style>
