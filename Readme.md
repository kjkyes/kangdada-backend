**Introduction**

一、核心业务流程图：

![image.png](https://github.com/kjkyes/kangdada-backend/blob/master/image.png)

---

二、接下来将详细介绍该项目的AI板块，包括AI生成题目和AI生成测评结果。

1. 本项目使用的是智谱AI大模型，之后的介绍以GLM-4-Air模型为例。

    请使用该代码的uu们注意：

        - 大家可以创建智谱AI大模型平台账号，新用户会赠送2000w免费tokens

        - 拥有帐号之后点击页面右上方的API keys，可使用默认的key，也可以自定义不同应用场景的key（切记开源项目前要隐藏好自己的key哦~）：

            ![api+key.png](AI智能测评系统+0fb84572-2877-4b8d-b1e0-79f627b38483/api+key.png)

            ![keys.png](AI智能测评系统+0fb84572-2877-4b8d-b1e0-79f627b38483/keys.png)

        - 大家也可以选用其他平台的大模型，查看他们的官方文档如何接入即可

1. 项目启动后，如果想用AI生成题目，请打开[接口文档](http://localhost:8101/api/doc.html#/default/app-controller/addAppUsingPOST)添加应用，示例如下：

    ```JSON
    {
      "appDesc": "测试下你的英文听力水平",
      "appIcon": "abc",
      "appName": "听力水平测试",
      "appType": 0,
      "scoringStrategy": 1
    }
    ```

    我们已经提前为大模型设置好了生成题目的prompt，只需将prompt所需的应用描述、名称、题目数量、选项个数补充完整即可。

    示例中appType为0，scoringStrategy为1表示该应用为得分类AI应用

    随后让我们制定好[AI生成题目](http://localhost:8101/api/doc.html#/default/question-controller/aiGenerateQuestionUsingPOST)的规则：

    ```JSON
    {   
      "appId": 1918277019943571458,
      "optionNum": 2,
      "questionNum": 5
    }
    ```

    这里我们随便设置，将prompt所需的题目数量、选项个数填充好即可。

1. AI生成题目如下：

    ```JSON
    {
      "code": 0,
      "data": [
        {
          "title": "What is the primary function of the application?",
          "options": [
            {
              "result": null,
              "score": 0,
              "value": "Improves language skills",
              "key": "A"
            },
            {
              "result": null,
              "score": 0,
              "value": "Offers live translations",
              "key": "B"
            }
          ]
        },
        {
          "title": "In which category can this application be found?",
          "options": [
            {
              "result": null,
              "score": 0,
              "value": "Education",
              "key": "A"
            },
            {
              "result": null,
              "score": 0,
              "value": "Entertainment",
              "key": "B"
            }
          ]
        },
        {
          "title": "What kind of exercises does the application provide?",
          "options": [
            {
              "result": null,
              "score": 0,
              "value": "Listening exercises",
              "key": "A"
            },
            {
              "result": null,
              "score": 0,
              "value": "Vocabulary quizzes",
              "key": "B"
            }
          ]
        },
        {
          "title": "Who is this application designed for?",
          "options": [
            {
              "result": null,
              "score": 0,
              "value": "For beginners",
              "key": "A"
            },
            {
              "result": null,
              "score": 0,
              "value": "For advanced learners",
              "key": "B"
            }
          ]
        },
        {
          "title": "Which feature is included in the application for a better user experience?",
          "options": [
            {
              "result": null,
              "score": 0,
              "value": "It has interactive videos",
              "key": "A"
            },
            {
              "result": null,
              "score": 0,
              "value": "It includes voice recognition",
              "key": "B"
            }
          ]
        }
      ],
      "message": "ok"
    }
    ```

1. 接着我们用一个MBTI性格测试的例子展示AI生成评测结果，我们提起准备10道题目（当然也可以让AI生成），我们对该应用填写自己的答案，比如：

    ```JSON
    {
      "appId": 3,
      "choices": ["A","B","A","B","A","A","B","A","A","A"]
    }
    ```

    随后我们可以看到AI生成的测评结果：

    ![mbti测评结果.png](AI智能测评系统+0fb84572-2877-4b8d-b1e0-79f627b38483/mbti测评结果.png)

    ```Markdown
    根据你提供的答案，你的MBTI性格类型为内向直觉思考判断（INTJ），这是一种较为罕见但非常强大的性格类型。你倾向于独立思考，具有战略眼光，能够在复杂问题中找到解决方案。你喜欢分析和理论，对于细节有很高的关注，能够在逻辑和客观分析的基础上做出决策。在社交场合中，你可能显得比较保守，更倾向于深度交流而非表面的闲聊。你重视效率，对时间的利用有严格的要求，通常不喜欢浪费在不必要的细节上。总的来说，你是一个有远见、有条理、决策果断的个体，非常适合从事需要分析和策略规划的职业。
    ```

以上就是我们展示的AI智能测评系统的部分功能，感兴趣的小伙伴们可以尝试改进，希望能帮到大家学习ai应用的开发！

