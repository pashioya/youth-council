import {getCsrfInfo} from "../../common/utils.js";

const facebook = document.getElementById('facebook');
const instagram = document.getElementById('instagram');
const twitter = document.getElementById('twitter');
const tiktok = document.getElementById('tiktok');

const form = document.getElementById('social-media-form');



/**
 * Update youth council social media links
 */
const updateSocialMedia = async () => {
    const facebookLink = facebook.value;
    const instagramLink = instagram.value;
    const twitterLink = twitter.value;
    const tiktokLink = tiktok.value;

    const data = {
        facebookLink: facebookLink,
        instagramLink: instagramLink,
        twitterLink: twitterLink,
        tiktokLink: tiktokLink
    };

    const res = await fetch('/api/webpages/social-media', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            ...getCsrfInfo()
        },
        body: JSON.stringify(data),
    });
    const response = await res.json();
        if (res.status === 200) {
           } else {
        console.error('Error updating social media links');
    }
}

/**
 * Populate social media links
 */
const populateSocialMedia = async () => {
    const res = await fetch('/api/webpages/social-media');
    const data = await res.json();
    // if blank, set to empty string
    for (let i = 0; i < data.length; i++) {
        switch (data[i].socialMedia) {
            case 'FACEBOOK':
                facebook.value = data[i].link;
                break;
            case 'INSTAGRAM':
                instagram.value = data[i].link;
                break;
            case 'TWITTER':
                twitter.value = data[i].link;
                break;
            case 'TIKTOK':
                tiktok.value = data[i].link;
                break;
            default:
                console.error('Invalid social media type');
        }
    }
    }



form.addEventListener('submit', (event) => {
    event.preventDefault();
    updateSocialMedia();
});
populateSocialMedia();
