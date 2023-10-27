import OpenAI from "openai";

const openai = new OpenAI({
  apiKey: process.env.REACT_APP_OPEN_AI_KEY,
  dangerouslyAllowBrowser: true,
});

export async function getBirdDescription(birdName) {
  const completion = await openai.chat.completions.create({
    messages: [
      {
        role: "system",
        content: `Tell me about the ${birdName} species of bird in less than 100 words.`,
      },
    ],
    model: "gpt-3.5-turbo",
  });
  console.log(completion.choices[0].message);
  return completion.choices[0].message.content;
}
