import axios from 'axios';

export async function getAllBreeds() {
    return await axios.get('http://localhost:8080/jobexam/vue/dogs');
    // return await axios.get('/vue/dogs');
}

export async function getBreedsLocalization(lang) {
    return await axios.get(`http://localhost:8080/jobexam/vue/dogs/breeds/${lang}`);
    // return await axios.get(`/vue/dogs/breeds/${lang}`);
}

export async function getRandomImageByBreed(breed) {
    return await axios.get(`http://localhost:8080/jobexam/vue/dogs/${breed}`);
    // return await axios.get(`/vue/dogs/${breed}`);
}

export async function sendDog(name, breed, comment, link) {
    // return axios.post(`/vue/dogs`, {
    return axios.post(`http://localhost:8080/jobexam/vue/dogs`, {
        name: name,
        breed: breed,
        comment: comment,
        link: link,
    });
}

