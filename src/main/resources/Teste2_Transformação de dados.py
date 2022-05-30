import re
import pdfplumber
import pandas as pd
from collections import namedtuple

#Criando a linha das colunas
Line = namedtuple(typename='Line',field_names='PROCEDIMENTO RN_Alteração VIGÊNCIA OD AMB HCO HSO REF PAC DUT SUBGRUPO GRUPO CAPÍTULO')
#Difinindo qual arquivo para ser lido
file = 'anexos/Anexo_I.pdf'

linhas = []
print('Aguardando...')
#Abrir o pdf com a biblioteca pdfplumber
with pdfplumber.open(file) as pdf:
    #Número total de paginas do pdf
    paginas = pdf.pages
    
    #For para vasculhar as páginas do pdf
    for indexPagina in range(len(paginas)):
            #Pegando o conteudo das paginas se tiver tabela (caso não tiver tabela na pagina retorna None)
            tabela_Pagina = pdf.pages[indexPagina].extract_table()
            #Verifica se possui tabela na pagina
            if(tabela_Pagina != None):
                #For para percorrer os dados da tabela
                for indexLinha in range(len(tabela_Pagina)):
                    #Pegando os valores de cada linha
                    valores = tabela_Pagina[indexLinha]
                    #Cada pagina o indice 0 possui o valores da colunas do arquivo (Procedimento, RN(alteração)...)
                    if indexLinha > 0:
                        #Pegando apenas os valores sem ser os titulos
                        for indexValor in range(len(valores)):
                            #Vasculhando os valores de cada linha
                            if valores[indexValor] == "AMB":
                                tabela_Pagina[indexLinha][indexValor] = "Seg. Ambulatorial"
                            elif valores[indexValor] == "OD":
                                tabela_Pagina[indexLinha][indexValor] = "Seg. Odontológica"
                #Vasculhando cada dado coletado das paginas
                for i in range(len(tabela_Pagina)):
                    if i > 0:
                        #Adicionando o valores nos seus devidos lugares (Procedimento = '', RN_Alteração = '' ...)
                        linhas.append(Line(*tabela_Pagina[i]))

df = pd.DataFrame(linhas)
df.head()

compression_opts = dict(method='zip',
                        archive_name='Anexo_I.csv')
df.to_csv('Teste_LucasEmmanuel.zip', index=False, encoding='utf-8-sig', compression=compression_opts)
print('Finalizado')
