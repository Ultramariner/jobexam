<script setup>
import { ref , onMounted } from 'vue'
import axios from 'axios';
import Dropdown from 'primevue/dropdown';
import InputText from 'primevue/inputtext';
import Image from 'primevue/image';
import Button from 'primevue/button';


let imgUrl = ref("blank.jpg");
let dogName = ref(null);
let dogComment = ref(null);
let breed = ref(null);
let breeds = ref(null);
const imgLoaded = ref(false);

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
    window.alert('Выберите породу');
    return;
  }
  try {
    const response = await axios.get(`http://localhost:8080/jobexam/vue/dogs/${breed.value}`);
    imgUrl.value = response.data;
  } catch (error) {
    console.error('Ошибка при получении изображения:', error);
  }
};

const save = async () => {
  if (!dogName.value) {
    window.alert('Заполните обязательные поля');
    return;
  }
  try {
    await axios.post(`http://localhost:8080/jobexam/vue/dogs`, {
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
  if (imgUrl.value !== "blank.jpg") {
    imgLoaded.value = true;
  }
};
</script>

<template>
  <div class="main-container">
    <div class="content-box">
      <form v-on:submit.prevent="formSubmit">
<!--        <template v-if="breeds">-->
<!--          <Dropdown v-model="breed" :options="breeds" optionLabel="name" placeholder="Выберите породу" />-->
<!--        </template>-->
<!--        <template v-else>-->
<!--          Загрузка...-->
<!--        </template>-->
<!--        <Dropdown v-model="breed" :options="breeds" optionLabel="name" @change="getImg" placeholder="Выберите породу" />-->
        <select v-model="breed" @change=getImg>
          <option value="" disabled selected>Выберите породу</option>
          <option v-for="breed in breeds" :key="breed.id" :value="breed.name">
            {{ breed.name }}
          </option>
        </select>
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
  max-width: 400px;
  height: auto;
}

select, input, button {
  margin-bottom: 10px;
  margin-right: 10px;
}
</style>