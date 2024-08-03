<script setup>
import './../styles/styles.css';
import {computed, onBeforeMount, ref} from 'vue'
import {getAllBreeds, getBreedsLocalization, getRandomImageByBreed, sendDog} from '@/scripts/api';
import Dropdown from 'primevue/dropdown';
import InputText from 'primevue/inputtext';
import Image from 'primevue/image';
import Button from 'primevue/button';
import Message from 'primevue/message';
import ProgressSpinner from 'primevue/progressspinner';

// todo ref required?
let imgUrl = ref("blank.jpg");
let dogName = ref();
let dogComment = ref();
let breed = ref();
let breeds = ref();
const isImgLoaded = ref(false);
let isImgLoading = ref(false);
const isSaved = ref(false);
const breedsMap = ref(new Map());

// todo just function call
// todo localization on click
onBeforeMount(async () => {
  try {
    const response = await getAllBreeds();
    breeds = Array.from(response.data);
    if (navigator.language !== 'en-US') {
      const responseLocalization = await getBreedsLocalization(navigator.language.toString());
      breeds.forEach((breed) => {
        if (responseLocalization.data[breed.name]) {
          breedsMap.value.set(breed.name, responseLocalization.data[breed.name]);
        } else {
          breedsMap.value.set(breed.name, breed.name);
        }
      });
    }
  } catch (error) {
    console.error('Ошибка при получении данных:', error);
  }
});

async function getImg() {
  if (!breed.value) {
    window.alert('Выберите породу');
    return;
  }
  try {
    isImgLoading.value = true;
    const response = await getRandomImageByBreed(breed.value.key);
    imgUrl.value = response.data;
    isImgLoading.value = false;
  } catch (error) {
    console.error('Ошибка при получении изображения:', error);
  }
}

async function save() {
  if (!dogName.value) {
    window.alert('Заполните обязательные поля');
    return;
  }
  try {
    const response = await sendDog(dogName.value, breed.value.key, dogComment.value, imgUrl.value);
    if (response.status === 200) {
      isSaved.value = true;
      setTimeout(() => {
        isSaved.value = false;
      }, 2000);
    }
  } catch (error) {
    console.error('Ошибка при загрузке данных:', error);
  }
}

async function handleImageLoad() {
  if (imgUrl.value !== "blank.jpg") {
    isImgLoaded.value = true;
  }
}

const breedsOptions = computed(() => {
  return Array.from(breedsMap.value).map(([key, value]) => ({key: key, label: value}));
});
</script>

<template>
  <div class="main-container">
    <div class="content-box">
      <form v-on:submit.prevent>
        <Dropdown v-model="breed" :options="breedsOptions" optionLabel="label" @change="getImg"
                  placeholder="Выберите породу" class="dropdown-with-margin"/>
        <InputText v-if="isImgLoaded" v-model="dogName" placeholder="Имя"/>
        <InputText v-if="isImgLoaded" v-model="dogComment" placeholder="Комментарий"/>
        <hr/>
        <ProgressSpinner v-if="isImgLoading" aria-label="Получение данных" />
        <Image v-if="isImgLoading === false" :src="imgUrl" alt="Фото" @load="handleImageLoad"/>
        <br/>
        <Button type="button" label="Поиск" icon="pi pi-search" @click="getImg"/>
        <Button v-if="isImgLoaded" type="button" label="Сохранить" icon="pi pi-save" @click="save"/>
        <Message v-if="isSaved" severity="success" class="message-box">Успешно сохранено!</Message>
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

.message-box {
  position: absolute;
  bottom: 30px;
  right: 50px;
}
</style>