import { ref } from "vue";

export const partyColors = [
    '#FF5C5C', '#8B5CF6', '#22C55E', '#3B82F6',
    '#FACC15', '#F97316', '#10B981', '#6366F1',
    '#EC4899', '#F59E0B', '#14B8A6', '#E11D48',
    '#0EA5E9', '#A78BFA', '#F43F5E', '#6B7280',
    '#FBBF24', '#34D399', '#60A5FA', '#F472B6',
    '#F87171', '#818CF8', '#FCD34D', '#4ADE80',
    '#C084FC', '#FDE68A'
];

/**
 * Provides a consistent color palette for parties and a helper to get a color by index.
 *
 * Usage:
 * const { getPartyColor } = usePartyColors();
 *
 * const color = getPartyColor(party.partyId); // returns the color based on the index in the color array
 */
export function usePartyColors() {
    /**
     * Get the color for a party based on its index.
     * Cycles through the predefined palette if index exceeds the array length.
     *
     * @param index - The index of the party in a list (I use id as the index to keep it consistent)
     * @returns A HEX color string
     */
    function getPartyColor(index: number) {
        return partyColors[index % partyColors.length];
    }

    return {
        partyColors,
        getPartyColor,
    };
}