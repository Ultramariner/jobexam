<script setup>
import {onMounted, ref} from "vue";
import Button from "primevue/button"
import {searchWelcomeMessage} from "@/services/WelcomeService";
import router from "@/router";
import axios from "axios";

let welcomeMessage = ref("Hello");
let isShowPage = ref(false);
onMounted(async () => {
    try {
        welcomeMessage.value = await searchWelcomeMessage();
        isShowPage.value=true;
    } catch (error){
        console.log(error);
        alert("Произошла ошибка загрузки страницы.");
    }
})

const getBreeds = async () => {
  try {
    await axios.get('http://localhost:8080/jobexam/api/dogs/breeds');
    // await axios.get('/vue/dogs/breeds');
  } catch (error) {
    console.error('Ошибка при загрузке данных:', error);
  }
};

/**
 * Переход на страницу сохранения картинок
 */
async function goToSavingPage() {
  await getBreeds();
  await router.push('/api/dogs');
}
</script>

<template>
    <div v-if="isShowPage" class="main-container">
        <div class="content-box">
            <h1 class="welcome-message">{{ welcomeMessage }}</h1>
            <div class="description">
                Good luck!
            </div>

            <Button type="button"
                    class="action-button"
                    @click="goToSavingPage"
            >
                Go to Dog Picture Saving Page
            </Button>
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