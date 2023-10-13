# bird up
A social media platform for bird watchers.

## 1. Problem Statement
These days, social media platforms tend to be hub of vanity, pretention and vitriol. While there are great sub-communities on most social existing platforms, they can be dificult to locate. Plus, social media platforms don't have domain-specific features as they are designed to support the widest range of communities. While this is a smart business move for a profit-maximizing corporation, the universality of these platforms comes at the cost of access to the most cutting edge domain-specific tools that are handcrafted with your community in mind. 

## 2. Technical Solution
Bird Up: a social media for bird watcher, with AI powered bird recognition, location specific species discovery, and platform for sharing and discussing your latest discoveries.

### Secenario 1:
Sharol loves birds and photography. She goes goes to her local state park every weekend in hopes of capturing a clear shot of her facorite species in the wild. One day, she spots a bird she has never seen before. She takes a picture of the bird and attempts to describe it in a google search. However, none of the birds that come up in her search results look like the bird in her picture. Thus, she goes goes home and uploads her photo. Instantly, she is given a prediction of the bird's species and a corresponging confidence percentage of 75%. Reference pictures pop up of this bird, and they look a lot like the one in her photo, as a well as a confirmation that the bird is native to her location. She is also informed that the bird is endangered, which may explain her unfamiliarity with the species. She posts her photo on the platform to share her rare discovery with her friends.

### Scenario 2:
Doug is on a business trip in a new town, and sees bird he doesn't recognize. He goes to tak e a picture, but the bird flies away before he gets that chance. He pulls up the bird Up website and types in his location. There he sees a long list of bird species and their photos. He filters them by color and he finds an appearant match. He posts news of this discovery to his friends.

## 3. Glossary

### User
A user account with an authorized role and inbox messages, with a relationship to friends (other users), and posts/sightings.

### Sighting
A sighting of a bird, containing an optional image, post text, likes and comments.

## 4. High Level Requirments
* Cloud hosted server and client
* Sign up a user (with limited role)
* Login
* Upload a picture and get results for species recognition
* Post with or without picture
* Location specific species search
* A feed of posts
* Add a friend
* Message a friend
* Comment on a post, like a post
* Search posts by species or location
* A map of bird sightings

## APIs
* Backend Java API
* eBird by Cornell Labs - find bird species by region, and get information about the region
* Nuthatch API - find species domain and genus, conservation status, and example pictures of birds
* bird_v2 from robflow - get ai powered bird species prediciton with confidence percentage.
