import api from "@/utils/axios-auth";
import {ref} from "vue";
export function useVoucher() {

    const loading = ref(false);
    const error = ref('');

    const getUserVoucher = async () => {
        try {
            loading.value = true;
            error.value = '';
            const response = await api.get('vouchers/my-vouchers');
            console.log(response.data)
            return response.data;

        } catch (err) {
            if (err.response) {
                error.value = err.response.data.error
            }
        } finally {
            loading.value = false;
        }
    };
}