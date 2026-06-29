"""HelloAgents 自定义异常模块"""


class HelloAgentsException(Exception):
    """HelloAgents 统一异常基类"""

    def __init__(self, message: str = "HelloAgents 发生了一个错误"):
        self.message = message
        super().__init__(self.message)

    def __str__(self):
        return self.message
