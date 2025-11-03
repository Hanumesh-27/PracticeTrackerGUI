const titleField = document.getElementById('title');
const topicField = document.getElementById('topic');
const diffField = document.getElementById('difficulty');
const codeArea = document.getElementById('codeSnippet');
const outputArea = document.getElementById('outputArea');
const searchField = document.getElementById('search');

const loadProblems = () => JSON.parse(localStorage.getItem('problems') || '[]');
const saveProblems = (data) => localStorage.setItem('problems', JSON.stringify(data));

const showProblems = (list) => {
  if (list.length === 0) {
    outputArea.innerHTML = "<p>No problems found!</p>";
    return;
  }
  outputArea.innerHTML = list.map(p => 
    `Title: ${p.title}\nTopic: ${p.topic}\nDifficulty: ${p.difficulty}\nDate: ${p.date}\nCode:\n${p.code}\n-----------------------------`
  ).join('\n\n');
};

document.getElementById('addBtn').addEventListener('click', () => {
  const title = titleField.value.trim();
  const topic = topicField.value.trim();
  const diff = diffField.value.trim();
  const code = codeArea.value.trim();

  if (!title || !topic || !diff || !code) {
    alert("Please fill all fields!");
    return;
  }

  const problems = loadProblems();
  problems.push({
    title,
    topic,
    difficulty: diff,
    code,
    date: new Date().toLocaleDateString()
  });
  saveProblems(problems);

  alert("✅ Problem added successfully!");
  titleField.value = topicField.value = diffField.value = codeArea.value = "";
  showProblems(problems);
});

document.getElementById('viewAllBtn').addEventListener('click', () => {
  showProblems(loadProblems());
});

document.getElementById('searchTopicBtn').addEventListener('click', () => {
  const keyword = searchField.value.trim().toLowerCase();
  const filtered = loadProblems().filter(p => p.topic.toLowerCase() === keyword);
  showProblems(filtered);
});

document.getElementById('searchDiffBtn').addEventListener('click', () => {
  const keyword = searchField.value.trim().toLowerCase();
  const filtered = loadProblems().filter(p => p.difficulty.toLowerCase() === keyword);
  showProblems(filtered);
});

// Load all on first open
showProblems(loadProblems());
