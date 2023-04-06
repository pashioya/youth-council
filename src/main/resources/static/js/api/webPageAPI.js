export const getWebPage = async (ycId) => {
    const response = await fetch(`/api/webpage`);
    return await response.json();
}
