/**
 * Calculates a safe percentage.
 * Returns '-' if the total is zero or undefined to avoid Infinity/NaN.
 *
 * @param part - The numerator value (e.g., votes counted)
 * @param total - The denominator value (e.g., total eligible voters)
 * @returns The percentage as a string with 2 decimals, or '-' if invalid
 */
export function safePercent(part: number, total: number): string {
    if (!total || total <= 0) return '-'; // or 'N/A', whatever you prefer
    return ((part / total) * 100).toFixed(2) + '%';
}