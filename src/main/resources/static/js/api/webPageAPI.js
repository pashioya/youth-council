export const getWebPage = async (ycId) => {
    const response = await fetch(`/api/youth-councils/${ycId}/webpage`);
    return await response.json();
}
