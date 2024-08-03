import axios from 'axios';

// todo (5) relative pathes
export async function getAllBreeds() {
    return await axios.get('http://localhost:8080/jobexam/api/dogs');
    // return await axios.get('/api/dogs');
}

export async function getBreedsLocalization(lang) {
    return await axios.get(`http://localhost:8080/jobexam/api/dogs/breeds/${lang}`);
    // return await axios.get(`/api/dogs/breeds/${lang}`);
}

export async function getRandomImageByBreed(breed) {
    return await axios.get(`http://localhost:8080/jobexam/api/dogs/${breed}`);
    // return await axios.get(`/api/dogs/${breed}`);
}

export async function sendDog(name, breed, comment, link) {
    // return axios.post(`/api/dogs`, {
    return axios.post(`http://localhost:8080/jobexam/api/dogs`, {
        name: name,
        breed: breed,
        comment: comment,
        link: link,
    });
}

