How to start this app:

1. Run the production schema in MySQL Workbench (location ./bird-up-server/sql/bird-up-production).
2. Use MySQL Workbench's 'Table Data Import Wizard' to insert data './bird-up-server/sql/NACC_list_species.csv' into the species table in the 'bird-up' database. Set the column 'common_name' to populate the table field 'species_short_name' and the column 'species' to populate the table field 'species_long_name'.
3. Open './bird-up-server' and run `App`.
4. Navigate to './bird-up-server' in the console and type 'npm i' in to install modules.
5. Create a '.env' file in the 'bird-up-server' root. Add the following environmental vairables:

REACT_APP_OPEN_AI_KEY -> generate an API key here: https://platform.openai.com/account/api-keys
REACT_APP_GOOGLE_KEY -> generate an API key here: https://developers.google.com/custom-search/v1/overview
REACT_APP_GOOGLE_CX -> create a programmable google search engine here: https://programmablesearchengine.google.com/about/
whenn you create a search engine, you are given a 'Search Engine ID'. This is your REACT_APP_GOOGLE_CX.

6. Enter 'npm start'.
