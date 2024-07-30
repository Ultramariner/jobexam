<script setup>
import { ref , onMounted } from 'vue'
import axios from 'axios';

let imgUrl = ref("blank.jpg");
let dogName = ref(null);
let dogComment = ref(null);
let breed = ref(null);
let breeds = ref(null);
const imgLoaded = ref(false);
// const errors = ref({
//   dogName: false,
//   breed: false,
// });

function formSubmit() {
  console.log('formSubmit', dogName)
  console.log('formSubmit', dogComment)
  console.log('formSubmit', breed)
  console.log('formSubmit', breeds)
  console.log('formSubmit', imgUrl)
}

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/jobexam/vue/dogs');
    breeds.value = Array.from(response.data);
  } catch (error) {
    console.error('Ошибка при получении данных:', error);
  }
});

const getImg = async () => {
  if (!breed.value) {
    // errors.value.breed = true;
    window.alert('Выберите породу');
    // return;
  }
  // errors.value.breed = false;
  try {
    const response = await axios.post(`http://localhost:8080/jobexam/vue/dogs?breed=${breed.value}`);
    imgUrl.value = response.data;
  } catch (error) {
    console.error('Ошибка при получении изображения:', error);
  }
};

const save = async () => {
  if (!dogName.value) {
    // errors.value.breed = true;
    window.alert('Заполните обязательные поля');
    return;
  }
  // errors.value.breed = false;
  try {
    await axios.put(`http://localhost:8080/jobexam/vue/dogs`, {
      name: dogName.value,
      breed: breed.value,
      comment: dogComment.value,
      link: imgUrl.value,
    });
  } catch (error) {
    console.error('Ошибка при загрузке данных:', error);
  }
};

const handleImageLoad = () => {
  if (imgUrl.value !== "blank1.jpg") {
    imgLoaded.value = true;
  }
};
</script>

<template>
  <div class="main-container">
    <div class="content-box">
      <form v-on:submit.prevent="formSubmit">
<!--        <select v-model="breed" @change=getImg :class="{ 'error': errors.value.breed }">-->
        <select v-model="breed" @change=getImg>
          <option value="" disabled selected>Выберите породу</option>
          <option v-for="breed in breeds">
            {{ breed.name }}
          </option>
        </select>
<!--        <input v-if="imgLoaded" type="text" v-model="dogName" class="form-control" placeholder="Имя" :class="{ 'error': errors.value.dogName }">-->
        <input v-if="imgLoaded" type="text" v-model="dogName" class="form-control" placeholder="Имя">
        <input v-if="imgLoaded" type="text" v-model="dogComment" class="form-control" placeholder="Комментарий">
        <hr/>
        <img :src= imgUrl  alt="Фото" @load="handleImageLoad">
        <br/>
        <button type="submit" class="btn btn-success" @click="getImg">Поиск</button>
        <button v-if="imgLoaded" type="submit" class="btn btn-success" @click="save">Сохранить</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.main-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.content-box {
  background-color: white;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  text-align: center;
}

img {
  max-width: 500px;
  height: auto;
}

/*.error {*/
/*  border: 2px solid red;*/
/*}*/
</style>