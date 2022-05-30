import zipfile
import tabula
import os
import camelot
from io import BytesIO

table = camelot.read_pdf("Anexo_I.pdf", pages="all")
print(table)

