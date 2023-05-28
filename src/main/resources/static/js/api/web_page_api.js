export const getWebPage = async (ycId) => {
    const response = await fetch(`/api/webpages`);
    return await response.json();
}
