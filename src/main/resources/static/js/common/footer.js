/**
 * Fetch and parse footer data
 */
// Fetch footer data
fetch('/api/webpages/social-media')
    .then(response => response.json())
    .then(data => {
        // Parse footer data
        parseFooter(data);
    })
    .catch(error => console.error(error)
);

const facebook = document.getElementById('facebook');
const instagram = document.getElementById('instagram');
const twitter = document.getElementById('twitter');
const tiktok = document.getElementById('tiktok');

/**
 * Parse footer data
 * @param data - json from the server
 */
const parseFooter = (data) => {
    // set href for each social media link
    data = data.filter((item) => item.link !== '');
    for (let i = 0; i < data.length; i++) {
        switch (data[i].socialMedia) {
            case 'FACEBOOK':
                // set to visible
                facebook.style.display = 'flex';
                facebook.href = data[i].link;
                break;
            case 'INSTAGRAM':
                instagram.style.display = 'flex';
                instagram.href = data[i].link;
                break;
            case 'TWITTER':
                twitter.style.display = 'flex';
                twitter.href= data[i].link;
                break;
            case 'TIKTOK':
                tiktok.style.display = 'flex';
                tiktok.href = data[i].link;
                break;
            default:
                console.error('Invalid social media type');
        }
    }
}
