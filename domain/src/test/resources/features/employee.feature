Feature: 员工管理
  作为组织管理员
  我想要管理员工
  以便有效地跟踪员工信息

  Scenario: 创建新员工
    Given 我有有效的员工信息，工号为"EMP001"，姓名为"John Doe"
    And id为"123e4567-e89b-12d3-a456-426614174000"的部门存在
    And id为"123e4567-e89b-12d3-a456-426614174001"的公司存在
    And 该部门属于该公司
    When 我创建一个新员工
    Then 员工应该被成功创建
    And 应该发布EmployeeCreatedEvent事件

  Scenario: 更新员工工号和姓名
    Given id为"123e4567-e89b-12d3-a456-426614174002"的员工存在
    When 我将员工工号更新为"EMP002"，姓名更新为"Jane Smith"
    Then 员工工号和姓名应该被更新
    And 应该发布EmployeeUpdatedEvent事件

  Scenario: 更新员工详细信息
    Given id为"123e4567-e89b-12d3-a456-426614174002"的员工存在
    When 我更新员工详细信息
    Then 员工详细信息应该被更新
    And 应该发布EmployeeInfoUpdatedEvent事件

  Scenario: 更新员工部门
    Given id为"123e4567-e89b-12d3-a456-426614174002"的员工存在
    And id为"123e4567-e89b-12d3-a456-426614174003"的新部门存在于同一公司
    When 我更新员工部门
    Then 员工部门应该被更新
    And 应该发布EmployeeDepartmentUpdatedEvent事件

  Scenario: 尝试将员工部门更新为不同公司的部门失败
    Given id为"123e4567-e89b-12d3-a456-426614174002"的员工存在
    And id为"123e4567-e89b-12d3-a456-426614174003"的新部门存在于不同公司
    When 我尝试更新员工部门
    Then 应该抛出异常，消息为"目标部门不属于员工当前所在的公司"

  Scenario: 激活员工
    Given id为"123e4567-e89b-12d3-a456-426614174002"的停用员工存在
    When 我激活该员工
    Then 员工应该被激活
    And 应该发布EmployeeActivatedEvent事件

  Scenario: 停用员工
    Given id为"123e4567-e89b-12d3-a456-426614174002"的活动员工存在
    When 我停用该员工
    Then 员工应该被停用
    And 应该发布EmployeeDeactivatedEvent事件