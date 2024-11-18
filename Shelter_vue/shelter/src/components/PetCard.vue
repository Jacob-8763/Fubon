<template>
    <div ref="exampleModal" class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">動物資料</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div v-if="pet">
                        <p>收容編號：{{ pet.id }}</p>
                        <p>種類：{{ pet.species }}</p>
                        <p v-if="pet.breed">品種：{{ pet.breed }}</p>
                        <p v-if="pet.color">花色：{{ pet.color }}</p>
                        <p v-if="pet.gender">性別：{{ pet.gender }}</p>
                        <p v-if="pet.status">狀態：{{ pet.status }}</p>
                        <p v-if="pet.createdDate">建檔時間：{{ formatDate(pet.createdDate) }}</p>
                        <p v-if="pet.arrivalDate">入所時間：{{ formatDate(pet.arrivalDate) }}</p>
                        <p v-if="pet.intakeReason">入所原因：{{ pet.intakeReason }}</p>
                        <p v-if="pet.departureDate">離所時間：{{ formatDate(pet.departureDate) }}</p>
                        <p v-if="pet.adopterName">領養人：{{ pet.adopterName }}</p>
                        <p v-if="pet.notes">備註：{{ pet.notes }}</p>
                        <div v-if="pet.photo" style="text-align: center;">
                            <img :src="path + pet.id" alt="pet photo" class="pet-image">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">關閉</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import bootstrap from 'bootstrap/dist/js/bootstrap.bundle.min.js';
import dayjs from 'dayjs';

const props = defineProps({
    pet: Object
});
const path = import.meta.env.VITE_PETPHOTO_URL;

const exampleModal = ref(null);
const exampleObj = ref(null);

// 格式化日期
function formatDate(date) {
    return dayjs(date).format('YYYY-MM-DD hh:mm:ss');
}

onMounted(function () {
    exampleObj.value = new bootstrap.Modal(exampleModal.value, { backdrop: 'static' });
});

function showModal() {
    exampleObj.value.show();
}

function hideModal() {
    exampleObj.value.hide();
}

defineExpose({
    showModal, hideModal
});
</script>

<style scoped>
.modal-dialog,
.modal-content {
    border: none;
    box-shadow: none;
    border-radius: 0.375rem;
}

img{
    width: 300px;
    height: 200px;
    object-fit: cover;
}

</style>