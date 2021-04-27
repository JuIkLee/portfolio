from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time

def get_replys(url):
    driver = webdriver.Chrome()
    driver.implicitly_wait(5)
    driver.get(url)

    driver.find_element_by_xpath('//*[@id="channelCouponLayer"]/div[1]/button').click()
    driver.find_element_by_css_selector('#goods_tab > ul.tt_tab > li#comment_tab > a').send_keys(Keys.ENTER)

    #while True:
    for i in range(10):
        try:
            more_read = driver.find_element_by_css_selector("a#moreData.btnMore.type02.comment").send_keys(Keys.ENTER)
            time.sleep(.3)
        except:
            break


    #print(html)
    # 모듈 참조
    from bs4 import BeautifulSoup
    html = driver.page_source
    soup = BeautifulSoup(html, 'lxml')  # html parser lxml이 제일 빠름

    #댓글 가져오기
    contents = soup.select('div.cons')
    contents = [content.text for content in contents]

    #닉
    nicks = soup.select('span.nick')
    nicks = [nick.text for nick in nicks ]

    #취합
    replys = list(zip(nicks, contents))
    return replys


url = 'http://m.hnsmall.com/goods/view/16503420?channel_code=20186&utm_source=Naver&utm_medium=AF&utm_campaign=TV_Beauty_16503420&NaPm=ct%3Dkbisuqgg%7Cci%3D445605e093955aaa3cf236d75c9656a6487e4226%7Ctr%3Dsls%7Csn%3D182824%7Chk%3D6c6b49fa4e03edb882a79488cb4173be9c087e78'

if __name__ == '__main__':
    from datetime import datetime
    reply_data = get_replys(url)

    import pandas as pd
    col = ['작성자', '내용']
    data_frame = pd.DataFrame(reply_data, columns=col)
    data_frame.to_excel('test.xlsx',sheet_name='shopping',startrow=0, header=True)
