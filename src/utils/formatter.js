// src/utils/formatUtils.js

/**
 * Formats a date string to German/European format (DD.MM.YYYY)
 * @param {string} dateString - The date string to format
 * @returns {string} Formatted date string
 */
export const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return new Intl.DateTimeFormat('de-DE', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    }).format(date);
};

/**
 * Formats a number as a Euro price in German/European format
 * @param {number} amount - The amount to format
 * @returns {string} Formatted price string
 */
export const formatPrice = (amount) => {
    if (amount == null) return '';
    return new Intl.NumberFormat('de-DE', {
        style: 'currency',
        currency: 'EUR',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(amount);
};


export const preventNonNumeric = (event) => {
    if (!/^[0-9.]$/.test(event.data)) {
        event.preventDefault()
    }

    // Prevent multiple decimal points
    if (event.data === '.' && event.target.value.includes('.')) {
        event.preventDefault()
    }
}