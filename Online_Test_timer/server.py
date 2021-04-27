from flask import Flask, render_template, request
app = Flask(__name__)

print(__name__)

@app.route('/')
def index():
     return render_template('timer_app.html') + render_template('Online_Test_html.html')


@app.route('/complete', methods =['POST'])
def button_Complete():
     name = request.form.get('name')

     # True or False radio buttons
     radio_1 = int(request.form.get('rd_1'))
     radio_2 = int(request.form.get('rd_2'))
     radio_3 = int(request.form.get('rd_3'))
     radio_4 = int(request.form.get('rd_4'))
     radio_5 = int(request.form.get('rd_5'))
     radio_6 = int(request.form.get('rd_6'))
     radio_7 = int(request.form.get('rd_7'))
     radio_8 = int(request.form.get('rd_8'))
     radio_9 = int(request.form.get('rd_9'))
     radio_10 = int(request.form.get('rd_10'))

     # multiple questions radio buttons
     mradio_1 = int(request.form.get('rdm_1'))
     mradio_2 = int(request.form.get('rdm_2'))
     mradio_3 = int(request.form.get('rdm_3'))
     mradio_4 = int(request.form.get('rdm_4'))
     mradio_5 = int(request.form.get('rdm_5'))

     #Sentence Completion Question 1
     textN_1 = request.form.get('q1_t1')
     textN_2 = request.form.get('q1_t2')
     if textN_1 == '인라인' :
          textA_1 = 5
     else :
          textA_1 = 0
     if textN_2 == '블록' :
          textA_2 = 5
     else :
          textA_2 = 0

     # Sentence Completion Question 2
     textN_3 = request.form.get('q2_t1')
     textN_4 = request.form.get('q2_t2')
     textN_5 = request.form.get('q2_t3')
     textN_6 = request.form.get('q2_t4')
     if textN_3 == 'post' or 'POST':
          textA_3 = 5
     else :
          textA_3 = 0
     if textN_4 == 'text-align' :
          textA_4 = 5
     else :
          textA_4 = 0
     if textN_5 == '&nbsp' :
          textA_5 = 5
     else :
          textA_5 = 0
     if textN_6 == 'display' :
          textA_6 = 5
     else :
          textA_6 = 0



     # Total score
     data = [radio_1, radio_2, radio_3, radio_4, radio_5, radio_6, radio_7, radio_8, radio_9, radio_10,
             mradio_1, mradio_2, mradio_3, mradio_4, mradio_5, textA_1, textA_2,textA_3,textA_4,textA_5,textA_6]
     result = sum(data)
     percent_score = int((result/65) *100)

     grade =''
     if percent_score >=90:
          grade = 'A'
     elif percent_score >= 80:
          grade = 'B'
     elif percent_score >=70:
          grade = 'C'
     else :
          grade = 'FAIL'

     return render_template('score_board.html', dataHtml= percent_score, nameHtml=name, gradeHtml=grade)


if __name__ == '__main__':
     app.run(debug=True)