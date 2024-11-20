<template>
    <tr @click="openCard(pet)">
        <td>{{ pet.id }}</td>
        <td><img :alt="pet.name" :src="path + pet.id"></td>
        <td>{{ pet.species }}</td>
        <td>{{ pet.breed }}</td>
        <td>{{ formatDate(pet.createdDate) }}</td>
        <td>{{ formatDate(pet.arrivalDate) }}</td>
        <td>{{ formatDate(pet.departureDate) }}</td>
        <td>{{ pet.status }}</td>
        <td>
            <button type="button" class="btn btn-success me-2" @click="handleEdit(pet, $event)">修改</button>
            <button type="button" class="btn btn-danger" @click="handleDelete(pet, $event)">刪除</button>
        </td>
    </tr>
    <pet-card ref="petCard" :pet="selectedPet">
    </pet-card>
</template>

<script setup>
import { ref } from 'vue';
import PetCard from './PetCard.vue';
import dayjs from 'dayjs';

const path = import.meta.env.VITE_PETPHOTO_URL
const props = defineProps(["pet"]);
const emits = defineEmits(["delete", "openUpdate"]);

const petCard = ref(null); 
let selectedPet = ref(null);

function formatDate(date) {
    return date ? dayjs(date).format('YYYY-MM-DD hh:mm:ss') : '-';
}

function openCard(pet) {
    selectedPet.value = pet;
    petCard.value.showModal();
}

function handleEdit(pet, event) {
    event.stopPropagation(); // 阻止事件冒泡
    emits('openUpdate', 'edit', pet.id);
}

function handleDelete(pet, event) {
    event.stopPropagation(); // 阻止事件冒泡
    emits('delete', pet.id);
}

</script>

<style scoped>
tr{
    text-align: center;
    vertical-align: middle;
    cursor: pointer;
}
tr:hover{
    background-color: antiquewhite;
}
img{
    width: 50px;
    height: 50px;
    object-fit: cover;
    border-radius: 8px;
}
</style>
