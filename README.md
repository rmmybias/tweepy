import tweepy
from other import keys


def api(28712478):
auth = tweepy. OAuthHandler(keys.T6j60NyLp29yC0vGKiODsV FTi, keys.u1bwcqJKu0G1gmKT5XDJG9 yD9ratTfQodyB2sKxb24zD QdqS2f)
auth.set_access_token(keys.1377926958579769350- dgHveFDmfIM1Piiq17m4iP HdQVcUIn,keys.QJHDGrzINTdIOFtwwWhJQq mR16ghtpIstKEgZtiMTXAO

return tweepy.API(auth)


def tweet(api: tweepy.API, message: str, image_path=None):
if image_path:
api.update_status_with_media (message, image_path)
else:
api.update_status (message)

print('Tweeted successfully!')

if name == 'main':




api = api()

tweet ( api, 'I'll try to be more active;((')
