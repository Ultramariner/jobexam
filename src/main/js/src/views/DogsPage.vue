<script setup>
import {ref, onBeforeMount, computed} from 'vue'
import axios from 'axios';
import Dropdown from 'primevue/dropdown';
import InputText from 'primevue/inputtext';
import Image from 'primevue/image';
import Button from 'primevue/button';


let imgUrl = ref("blank.jpg");
let dogName = ref(null);
let dogComment = ref(null);
let breed = ref(null);
let breeds = ref();
let breedsList = ref([]);
const imgLoaded = ref(false);
const breedsMap = ref(new Map());

onBeforeMount(async () => {
  try {
    const response = await axios.get('http://localhost:8080/jobexam/vue/dogs');
    const responseLocalization = await axios.get('http://localhost:8080/jobexam/vue/dogs/breeds/ru');
    breeds.value = Array.from(response.data);
    breeds.value.forEach((breed) => {
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


const getImg = async () => {
  if (!breed.value) {
    window.alert('Выберите породу');
    return;
  }
  try {
    const response = await axios.get(`http://localhost:8080/jobexam/vue/dogs/${breed.value.key}`);
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
      breed: breed.value.key,
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

.dropdown-with-margin {
  margin-bottom: 10px;
  margin-right: 10px;
}

img {
  max-width: 400px;
  height: auto;
}

input, button {
  margin-bottom: 10px;
  margin-right: 10px;
}
</style>