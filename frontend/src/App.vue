<script setup lang="ts">
    import Header from './componentsLayout/Header.vue';
    import Footer from './componentsLayout/Footer.vue';

</script>

<template>

    <Header></Header>

    <div id="app">
        <router-view />
    </div>

    <Footer></Footer>

</template>

<style scoped></style>
