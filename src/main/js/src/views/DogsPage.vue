<script setup>
import { ref , onMounted } from 'vue'
import axios from 'axios';

let imgUrl = ref(null);
let dogName = ref(null);
let dogComment = ref(null);
let breed = ref(null);
let breeds = ref(null);

// function search() {
// }
//
// function save() {
// }

function formSubmit() {
  console.log('formSubmit', dogName)
  console.log('formSubmit', dogComment)
  console.log('formSubmit', breed)
  console.log('formSubmit', breeds)
  console.log('formSubmit', imgUrl)
}

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8080/jobexam/vue/dogs/breeds');
    breeds.value = Array.from(response.data);
  } catch (error) {
    console.error('Ошибка при загрузке данных:', error);
  }
});

const fetchData = async () => {
  try {
    const response = await axios.get('http://localhost:8080/jobexam/vue/dogs', {
      params: {
        breed: 'hound'
      }
    });
    imgUrl.value = response.data;
  } catch (error) {
    console.error('Ошибка при загрузке данных:', error);
  }
};
</script>

<template>
  <div class="main-container">
    <div class="content-box">
      <form v-on:submit.prevent="formSubmit">
        <select v-model="breed">
          <option v-for="breed in breeds">
            {{ breed }}
          </option>
        </select>
        <input type="text" v-model="dogName" class="form-control">
        <hr/>
        <input type="text" v-model="dogComment" class="form-control">
        <hr/>
        <img :src= imgUrl  alt="">
        <br/>
        <button type="submit" class="btn btn-success" @click="fetchData">Поиск</button>
<!--        <button type="submit" class="btn btn-success">Сохранить</button>-->
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

.welcome-message {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 16px;
}

.description {
  font-size: 18px;
  color: #666;
  margin-bottom: 24px;
}

.action-button {
  background-color: #007bff;
  color: white;
  font-size: 16px;
  padding: 12px 24px;
  border-radius: 4px;
  cursor: pointer;
}

.action-button:hover {
  background-color: #0056b3;
}
</style>