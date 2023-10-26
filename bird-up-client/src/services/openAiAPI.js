import OpenAI from "openai";

const KEY = "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";
// set apiKey by running command in console:
// setx OPENAI_API_KEY "your-api-key-here"

const openai = new OpenAI({ apiKey: KEY, dangerouslyAllowBrowser: true });

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
