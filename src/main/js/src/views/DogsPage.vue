<script setup>
import './../styles/styles.css';
import {ref, onBeforeMount, computed} from 'vue'
import axios from 'axios';
import Dropdown from 'primevue/dropdown';
import InputText from 'primevue/inputtext';
import Image from 'primevue/image';
import Button from 'primevue/button';

//todo ref required?
let imgUrl = ref("blank.jpg");
let dogName = ref();
let dogComment = ref();
let breed = ref();
let breeds = ref();
const imgLoaded = ref(false);
const breedsMap = ref(new Map());

//todo just function call
onBeforeMount(async () => {
  try {
    const response = await getAllBreeds();
    const responseLocalization = await getBreedsLocalization("ru");
    breeds = Array.from(response.data);
    breeds.forEach((breed) => {
      if (responseLocalization.data[breed.name]) {
        breedsMap.value.set(breed.name, responseLocalization.data[breed.name]);
      } else {
        breedsMap.value.set(breed.name, breed.name);
      }
    });
  } catch (error) {
    console.error('Ошибка при получении данных:', error);
  }
});

//todo (5) relative pathes
async function getAllBreeds() {
  return await axios.get('http://localhost:8080/jobexam/vue/dogs');
  // return await axios.get('/vue/dogs');
}

async function getBreedsLocalization(lang) {
  return await axios.get(`http://localhost:8080/jobexam/vue/dogs/breeds/${lang}`);
  // return await axios.get(`/vue/dogs/breeds/${lang}`);
}

async function getImg() {
  if (!breed.value) {
    window.alert('Выберите породу');
    return;
  }
  try {
    const response = await getRandomImageByBreed(breed.value.key);
    imgUrl.value = response.data;
  } catch (error) {
    console.error('Ошибка при получении изображения:', error);
  }
}

async function getRandomImageByBreed(breed) {
  return await axios.get(`http://localhost:8080/jobexam/vue/dogs/${breed}`);
  // return await axios.get(`/vue/dogs/${breed}`);
}

//todo alert if saved
async function save() {
  if (!dogName.value) {
    window.alert('Заполните обязательные поля');
    return;
  }
  try {
    await sendDog(dogName.value, breed.value.key, dogComment.value, imgUrl.value)
  } catch (error) {
    console.error('Ошибка при загрузке данных:', error);
  }
}

async function sendDog(name, breed, comment, link) {
  // return axios.post(`/vue/dogs`, {
  return axios.post(`http://localhost:8080/jobexam/vue/dogs`, {
    name: name,
    breed: breed,
    comment: comment,
    link: link,
  });
}

async function handleImageLoad() {
  if (imgUrl.value !== "blank.jpg") {
    imgLoaded.value = true;
  }
}

const breedsOptions = computed(() => {
  return Array.from(breedsMap.value).map(([key, value]) => ({ key: key, label: value }));
});
</script>

<template>
  <div class="main-container">
    <div class="content-box">
      <form v-on:submit.prevent="formSubmit">
        <Dropdown v-model="breed" :options="breedsOptions" optionLabel="label" @change="getImg" placeholder="Выберите породу" class="dropdown-with-margin" />
        <InputText v-if="imgLoaded" v-model="dogName" placeholder="Имя" />
        <InputText v-if="imgLoaded" v-model="dogComment" placeholder="Комментарий" />
        <hr/>
        <Image :src="imgUrl" alt="Фото" @load="handleImageLoad" />
        <br/>
        <Button type="button" label="Поиск" icon="pi pi-search" @click="getImg" />
        <Button v-if="imgLoaded" type="button" label="Сохранить" icon="pi pi-save" @click="save" />
      </form>
    </div>
  </div>
</template>

<style scoped>
img {
max-width: 400px;
height: auto;
}

input, button, .dropdown-with-margin {
margin-bottom: 10px;
margin-right: 10px;
}
</style>